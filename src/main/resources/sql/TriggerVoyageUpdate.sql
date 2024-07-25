use waterways;

drop trigger if exists VoyageUpdateTrigger;

delimiter $

create trigger VoyageUpdateTrigger before update
    on Voyage for each row
begin
    if OLD.DepartureTime < now() then
        signal sqlstate '45000' set message_text = 'Cannot update completed voyages';
    end if;

    if OLD.ArrivalHarborId != NEW.ArrivalHarborId
        or OLD.DepartureHarborId != NEW.DepartureHarborId
        or OLD.ArrivalTime != NEW.ArrivalTime
        or OLD.DepartureTime != NEW.DepartureTime
        or OLD.ShipSerialId != NEW.ShipSerialId
        or OLD.VoyageId != NEW.VoyageId
       then
           signal sqlstate '45000' set message_text = 'Cannot update connecting id, harbors, times or carrier for voyage';
    end if;
    
    if exists(
        select * from Harbor where (HarborId = NEW.ArrivalHarborId OR HarborId = NEW.DepartureHarborId) AND HarborStatusCode != 1
    ) then
        signal sqlstate '45000' set message_text = 'Connecting harbors are not operational';
    end if;

    if exists (
        select * from Ship WHERE Ship.ShipSerialId = NEW.ShipSerialId AND ShipStatusCode != 1
    ) then
        signal sqlstate '45000' set message_text = 'Carrier is not operational';
    end if ;

    if OLD.VoyageStatusCode = 1 and NEW.VoyageStatusCode = 2 then
        insert into Transaction (TransactionDate, Amount, UserId) (
            select now(), -sum(Transaction.Amount), Transaction.UserId
            from Transaction, RoomBooking
            where RoomBooking.TransactionId = Transaction.TransactionId
              and RoomBooking.VoyageId = OLD.VoyageId
            group by Transaction.UserId
        );

        insert into Transaction (TransactionDate, Amount, UserId) (
            select now(), -sum(Transaction.Amount), Transaction.UserId
            from Transaction, FoodBooking
            where Transaction.TransactionId = FoodBooking.TransactionId
              and FoodBooking.VoyageId = OLD.VoyageId
            group by Transaction.UserId
        );

        update RoomBooking set TransactionId = null, RoomStatusCode = 1
        where RoomBooking.VoyageId = NEW.VoyageId;

        delete from FoodBooking where FoodBooking.VoyageId = NEW.VoyageId;
    end if;
end;

$

delimiter ;
