-- Create table
create global temporary table TY_IMEI_TEMP
(
  imei varchar2(50)
)
on commit delete rows;