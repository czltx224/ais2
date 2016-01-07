--查询主表字段
select finfo.obj_tablename,ffield.field_name,ffield.label_name
from flow_forminfo finfo,flow_formfield ffield 
where finfo.id=ffield.form_id 
and finfo.id in(
select fl.oid from flow_pipeinfo fp,flow_forminfo ff,flow_formlink fl
where fp.form_id=ff.id and ff.id=fl.pid 
) and finfo.table_type=1 and ffield.status=1
--查询主表值