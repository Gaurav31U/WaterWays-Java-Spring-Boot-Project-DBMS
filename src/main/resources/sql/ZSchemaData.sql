# SHIP_STATUS:
#     1 - OPERATIONAL
#     2 - SUSPENDED
#     3 - MAINTENANCE
#
# VOYAGE_STATUS:
#     1 - OPERATIONAL
#     2 - COMPLETED
#     3 - CANCELLED
#
# ROOM_STATUS:
#     1 - AVAILABLE
#     2 - BOOKED
#     3 - RESERVED
#     4 - MAINTENANCE

use waterways;

insert into ShipModel (RoomCount, ModelName) values
(200, 'Cruiser 1'),
(50, 'Ferry 1'),
(2, 'House Boat'),
(150, 'Cruiser 2'),
(170, 'Cruiser 3'),
(70, 'Ferry 2'),
(90, 'Ferry 3');



insert into Ship (ModelId, ShipStatusCode, MfDate) values
(1, 1, '2000-01-01 00:00:00'),
(2, 1, '2001-01-01 00:00:00'),
(3, 1, '2002-01-01 00:00:00'),
(4, 1, '2003-01-01 00:00:00'),
(5, 1, '2004-01-01 00:00:00'),
(6, 1, '2005-01-01 00:00:00'),
(7, 1, '2006-01-01 00:00:00'),
(1, 1, '2007-01-01 00:00:00');



insert into Users (UserName, EmailId, UserPassword) values
('Priyanshu', 'priyanshu@gmail.com', '1234'),
('Kushagra', 'kushagra@gmail.com', '1234'),
('Yogesh', 'yogesh@gmail.com', '1234'),
('Harsh', 'harsh@gmail.com', '1234'),
('Gaurav', 'gaurav@gmail.com', '1234'),
('Rahul', 'rahul@gmail.com', '1234'),
('Vansh', 'vansh@gmail.com', '1234'),
('Shivam', 'shivam@gmail.com', '1234'),
('Deepanshu', 'deepanshu@gmail.com', '1234'),
('Chirag', 'chirag@gmail.com', '1234'),
('Devashish', 'devashish@gmail.com', '1234'),
('Milan', 'milan@gmail.com', '1234'),
('Anas', 'anas@gmail.com', '1234'),
('Nitin', 'nitin@gmail.com', '1234');



insert into Employee (UserId, JoinDate, EmployeeStatusCode) values
(1, '2020-01-01 00:00:00', 1),
(2, '2020-02-01 00:00:00', 1),
(3, '2020-03-01 00:00:00', 1),
(4, '2020-04-01 00:00:00', 1),
(5, '2020-07-01 00:00:00', 1),
(6, '2020-06-01 00:00:00', 1),
(7, '2020-08-01 00:00:00', 1),
(8, '2020-09-01 00:00:00', 1),
(9, '2020-05-01 00:00:00', 1);



insert into Harbor (Location, ConstructionDate, HarborStatusCode, ManagerId) values
('Chennai', '2002-01-01 00:00:0', 1, 1),
('Mumbai', '2002-01-01 00:00:0', 1, 2),
('Dubai', '2002-01-01 00:00:0', 1, 3),
('Singapore', '2002-01-01 00:00:0', 1, 1),
('Shanghai', '2002-01-01 00:00:0', 1, 3),
('Tokyo', '2002-01-01 00:00:0', 1, 3),
('Miami', '2002-01-01 00:00:0', 1, 1),
('Cape Town', '2002-01-01 00:00:0', 1, 2);



insert into Voyage (ShipSerialId, Fare, DepartureHarborId, ArrivalHarborId, DepartureTime, ArrivalTime, VoyageStatusCode) values
(1, 20000, 1, 2, '2022-09-10 06:30:0', '2022-09-23 15:20:0', 1),
(2, 10000, 3, 5, '2022-10-12 06:30:0', '2022-10-22 15:20:0', 1),
(4, 22000, 2, 4, '2022-05-17 06:30:0', '2022-06-01 15:20:0', 1),
(5, 30000, 4, 3, '2022-11-10 06:30:0', '2022-11-22 15:20:0', 1),
(3, 27000, 3, 1, '2022-12-01 06:30:0', '2022-12-23 15:20:0', 1),
(2, 14000, 1, 5, '2022-12-10 06:30:0', '2022-12-27 15:20:0', 1),
(5, 19000, 3, 2, '2022-11-20 06:30:0', '2022-12-06 15:20:0', 1),
(1, 21000, 4, 5, '2022-11-26 06:30:0', '2022-12-10 15:20:0', 1),
(2, 18000, 6, 4, '2022-11-09 06:30:0', '2022-11-30 15:20:0', 1),
(4, 25000, 6, 5, '2022-09-30 06:30:0', '2022-10-21 15:20:0', 1);



insert into Crew (EmployeeId, VoyageId, CrewRole) values
(4, 1, 'Captain'),
(4, 4, 'Captain'),
(4, 3, 'Chef'),
(5, 2, 'Medic'),
(5, 5, 'Engineer'),
(6, 6, 'Manager'),
(6, 7, 'Captain'),
(7, 3, 'Engineer'),
(7, 5, 'Medic'),
(7, 10, 'Chef'),
(8, 8, 'Captain'),
(8, 1, 'Emergency'),
(9, 9, 'Captain');



insert into FoodItem (FoodItemId, VoyageId, FoodCost, FoodName, FoodDescription) values
(1, 1, 30, 'Chocolate', 'Common chocolates'),
(2, 1, 30, 'Ice Cream', 'Vanilla, Strawberry, Butterscotch'),
(3, 1, 30, 'Pan Cake', ''),
(1, 2, 30, 'Pasta', 'White Sauce, Tomato Sauce'),
(2, 2, 30, 'Sandwich', 'Paneer tikka, Vegetable, Cheese'),
(1, 3, 30, 'Pizza', 'Varieties'),
(2, 3, 30, 'Coffee', 'Filter Coffee'),
(1, 4, 30, 'Milkshake', 'Vanilla, Strawberry, Butterscotch'),
(2, 4, 30, 'Tea', 'Darjeeling tea'),
(3, 4, 30, 'Biscuits', 'Varieties'),
(1, 5, 30, 'Roll', 'Paneer tikka, Vegetable, Cheese'),
(2, 5, 30, 'Fries', 'with peri peri'),
(1, 6, 30, 'Brownies', 'fresh'),
(2, 6, 30, 'Cookies', 'Choco chip'),
(1, 7, 30, 'Pastry', 'Vanilla, Strawberry, Butterscotch'),
(2, 7, 30, 'Cup Cake', 'Vanilla, Strawberry, Butterscotch'),
(1, 8, 30, 'Pudding', 'Vanilla, Strawberry, Butterscotch'),
(1, 9, 30, 'Pineapple', 'per slice'),
(1, 10, 30, 'Banana', 'fresh');

