use waterways;

drop trigger if exists FoodItemDeleteTrigger;

delimiter $

create trigger FoodItemDeleteTrigger before delete
    on FoodItem for each row
begin
    if (
        select ArrivalTime
        from Voyage
        where Voyage.VoyageId = @VoyageId
    ) < now() then
        signal sqlstate '45000' set message_text = 'Cannot delete FoodItem that was served on a completed voyage';
    end if;

    insert into Transaction (TransactionDate, Amount, UserId) (
        select now(), -sum(Transaction.amount), Transaction.UserId
        from Transaction, FoodBooking
        where FoodBooking.TransactionId = Transaction.TransactionId
          and FoodBooking.FoodItemId = @FoodItemId
          and FoodBooking.VoyageId = @VoyageId
        group by UserId
    );

end $

delimiter ;