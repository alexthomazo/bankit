INSERT INTO `categories` (`category_id`, `name`) VALUES
(1, 'Carburant'),
(2, 'Communications'),
(3, 'Alimentation'),
(4, 'Divers');

INSERT INTO `operations` (`operation_id`, `value_date`, `operation_date`, `label`, `planned`, `amount`, `category_id`) VALUES
(16,'2012-05-12', '2012-05-12', 'Init account', NULL, 2147.24, NULL),
(15,'2012-05-24', '2012-05-24', 'Free mobile', NULL, -29.99, 2),
(14, NULL,        '2012-06-14', 'PLANNED', -200, NULL, NULL),
(1, '2012-07-20', '2012-07-21', 'ESSENCE', NULL, -73.07, 1),
(2, '2012-07-27', '2012-07-27', 'SALAIRE', NULL, 1703.14, NULL),
(11,'2012-08-01', '2012-08-01', 'LOYER', NULL, -600, 4),
(3, '2012-08-14', '2012-08-14', 'SUPERMARCHE', NULL, -45.87, 3),
(4, '2012-08-15', '2012-08-13', 'EPARGNE', -500, -500, NULL),
(5, '2012-08-15', '2012-08-15', 'SUPERMARCHE', NULL, -21.45, 3),
(6, '2012-08-20', '2012-08-20', 'INTERNET', -29.99, -29.99, 2),
(7, '2012-08-20', '2012-08-20', 'RETRAIT', NULL, -20, NULL),
(8, '2012-08-21', '2012-08-21', 'SUPERMARCHE', NULL, -73.07, 3),
(9, NULL, '2012-08-31', 'ASSURANCE', -140, NULL, NULL),
(10, NULL, '2012-08-24', 'TELEPHONE MOBILE', -19.99, NULL, 2),
(12, NULL, '2012-08-27', 'Impots Revenu', -845, NULL, NULL);


INSERT INTO `costs` (`cost_id`, `day`, `label`, `amount`, `category_id`) VALUES
(1, 27, 'VIR SALAIRE', 1700.14, NULL),
(2, 1, 'VIR LOYER', -595.12, NULL),
(3, 3, 'PRLV Assurance Auto', -70.48, NULL),
(4, 24, 'PRLV Free Mobile', -19.99, 2);

INSERT INTO `options` (`option_id`, `name`, `string_val`, `date_val`, `int_val`) VALUES
(11, 'testString', 'test string', NULL, NULL),
(12, 'testDate', NULL, '1984-12-22', NULL),
(13, 'testInteger', NULL, NULL, 22),