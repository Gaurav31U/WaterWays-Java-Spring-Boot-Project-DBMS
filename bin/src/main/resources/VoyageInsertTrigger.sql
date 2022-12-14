use waterways;

drop trigger if exists VoyageInsertTrigger;

delimiter $

create trigger VoyageInsertTrigger after insert
    on Voyage for each row
begin
    set @count = (
        select RoomCount from ShipModel where ShipModel.ModelId = @ModelId
    );

    set @i = 1;

    while @i <= @count do
        insert into RoomBooking (RoomId, VoyageId, TransactionId, RoomStatusCode)
        values (@i, @VoyageId, null, (SELECT RoomStatusCode from ROOM_STATUS where RoomStatusDesc = 'AVAILABLE'));
        set @i = @i + 1;
    end while;
end $

delimiter ;
