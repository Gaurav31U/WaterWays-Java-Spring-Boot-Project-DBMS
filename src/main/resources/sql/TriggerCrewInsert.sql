use waterways;
drop trigger if exists CrewInsertTrigger;

delimiter $

create trigger CrewInsertTrigger before insert
    on Crew for each row
begin

    if (
        (SELECT EmployeeStatusCode FROM Employee WHERE Employee.EmployeeId = NEW.EmployeeId) = 2
        or
        exists(
            SELECT * FROM (
                SELECT Voyage.DepartureTime as dtime, Voyage.ArrivalTime as atime FROM Crew INNER JOIN Voyage
                ON Crew.EmployeeId = NEW.EmployeeId AND Crew.VoyageId = Voyage.VoyageId
            ) as WillServe, (
                SELECT DepartureTime as dtime, ArrivalTime as atime FROM Voyage WHERE Voyage.VoyageId = NEW.VoyageId
            ) as NewVoyage
            WHERE
                WillServe.atime > NewVoyage.dtime AND WillServe.dtime < NewVoyage.atime
        )
    ) then
        signal sqlstate '45000' set message_text = 'Employee serves on another voyage at the same time';
    END IF;
end;

$

delimiter ;