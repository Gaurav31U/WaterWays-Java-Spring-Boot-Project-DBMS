use waterways;

drop view if exists ShipAdminView;

create view ShipAdminView as
    select ShipSerialId, SM.ModelName as ModelName, MfDate, ShipStatusCode
    from Ship
            inner join ShipModel SM on Ship.ModelId = SM.ModelId
;


drop view if exists VoyageUserView;

create view VoyageUserView as
    select VoyageId,
           Fare,
           Voyage.ShipSerialId as ShipSerialId,
           SM.ModelName as ModelName,
           Hd.HarborId as DepartureHarborId,
           Ha.HarborId as ArrivalHarborId,
           Hd.Location as DepartureHarborName,
           Ha.Location as ArrivalHarborName,
           DepartureTime,
           ArrivalTime,
           VoyageStatusCode
    from Voyage
            inner join Ship S on Voyage.ShipSerialId = S.ShipSerialId
            inner join ShipModel SM on S.ModelId = SM.ModelId
            inner join Harbor Ha on Voyage.ArrivalHarborId = Ha.HarborId
            inner join Harbor Hd on Voyage.DepartureHarborId = Hd.HarborId
;


drop view if exists FoodBookingAdminView;

create view FoodBookingAdminView as
    select U.UserName as UserName,
           FoodBooking.TransactionId as TransactionId,
           RoomId,
           FoodBooking.VoyageId as VoyageId,
           FoodName,
           FoodDescription,
           FoodItemCount
    from FoodBooking
            inner join FoodItem on FoodBooking.VoyageId = FoodItem.VoyageId and FoodBooking.FoodItemId = FoodItem.FoodItemId
            inner join Transaction T on FoodBooking.TransactionId = T.TransactionId
            inner join Users U on T.UserId = U.UserId
;



drop view if exists FoodBookingView;

create view FoodBookingView as
select U.UserName as UserName,
       FoodBooking.TransactionId as TransactionId,
       RoomId,
       FoodBooking.VoyageId as VoyageId,
       FoodName,
       FoodDescription,
       FoodItemCount
from FoodBooking
         inner join FoodItem on FoodBooking.VoyageId = FoodItem.VoyageId and FoodBooking.FoodItemId = FoodItem.FoodItemId
         inner join Transaction T on FoodBooking.TransactionId = T.TransactionId
         inner join Users U on T.UserId = U.UserId
;
