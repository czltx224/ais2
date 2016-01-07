create or replace procedure PRO_TIMING_CUS is
endCountDate  Date;
ductendCountDate    Date;/*产品结构最后一次统计时间*/
fitendCountDate        Date;/*盈利性最后一次统计时间*/
ca_endCountDate  Date;
accountCount      Number;
pa_accountCount   Number;
cp_accountCount   Number;
begin
    select count(*) into cp_accountCount from cus_producttype;
    if cp_accountCount=0 then
       ductendCountDate:=to_date('2010-1-1','yyyy-mm-dd');
    else
       select max(t.procude_time) into ductendCountDate from cus_producttype t;
    end if;
    
    select count(*) into pa_accountCount from cus_profit;
    if pa_accountCount=0 then
       fitendCountDate:=to_date('2010-1-1','yyyy-mm-dd');
    else
       select max(t.pro_time)into fitendCountDate from cus_profit t;
    end if;
    
    insert into cus_producttype(id,product_type,procude_time,pro_weight,cus_id) select seq_cus_product_type.nextval,tabpro.counttype,sysdate,tabpro.sendSum,tabpro.cus_id
    from
    (
      select t.cus_id,'市内送货' counttype,nvl(sum(case when t.take_mode = '市内送货' and t.distribution_mode='新邦' then t.cq_weight end),0) sendSum
      from opr_fax_in t where t.create_time>ductendCountDate and t.status=1 group by t.cus_id
      union
      select t.cus_id,'市内自提' counttype,nvl(sum(case when t.take_mode = '市内自提' and t.distribution_mode='新邦' then t.cq_weight end),0) sendSum
      from opr_fax_in t where t.create_time>ductendCountDate and t.status=1 group by t.cus_id
      union
      select t.cus_id,'专车' counttype,nvl(sum(case when t.sonderzug=1 and t.distribution_mode='新邦' then t.cq_weight end),0) sendSum
      from opr_fax_in t where t.create_time>ductendCountDate and t.status=1 group by t.cus_id
      union
      select t.cus_id,'中转' counttype,nvl(sum(case when t.distribution_mode='中转' then t.cq_weight end),0) sendSum
      from opr_fax_in t where t.create_time>ductendCountDate and t.status=1 group by t.cus_id
      union
      select t.cus_id,'外发' counttype,nvl(sum(case when t.distribution_mode='外发' then t.cq_weight end),0) sendSum
      from opr_fax_in t where t.create_time>ductendCountDate and t.status=1 group by t.cus_id 
    ) tabpro;
    /*盈利分析*/
    insert into cus_profit(id,pro_time,fi_income,fi_cost,cus_id)
    select seq_cus_profit.nextval,sysdate,tf.sumincome,tf.sumcost,tf.cus_id
    from
    (
   select ofi.cus_id,sum(fi.amount) sumincome,
      sum(fc.cost_amount) sumcost
      from fi_income fi,opr_fax_in ofi,fi_cost fc where fi.d_no=ofi.d_no and ofi.d_no=fc.d_no and ofi.create_time>fitendCountDate and ofi.status=1 group by ofi.cus_id
     ) tf;

    /*生命周期分类*/
    update cus_record cr
    set cr.develop_level='流失客户'
    where sysdate-cr.last_buss>cr.delivery_cycle;

    update cus_record cr
    set cr.develop_level='危机客户'
    where sysdate-cr.last_buss>cr.warn_delivery_cycle and (sysdate-cr.last_buss)<=cr.delivery_cycle;

    /*销售机会  指标时间使用率*/
    update cus_sale_chance t set t.time_user=round((sysdate-t.start_time)/(t.end_time-t.start_time),2);

    /*客户重要程度分类*/
    update cus_record c set
        c.information='大客户' where c.cus_id in(
         select cus_id from(select ofi.cus_id,sum(decode(to_date(to_char(ofi.create_time,'yyyy-mm'),'yyyy-mm'),to_date(to_char(sysdate,'yyyy-mm'),'yyyy-mm'),(ofi.cp_fee+ofi.consignee_fee+ofi.sonderzug_price+ofi.cp_value_add_fee+ofi.cus_value_add_fee+ofi.cp_sonderzug_price),0)) profitFee from opr_fax_in ofi group by ofi.cus_id)
             where profitFee>=50000
      );
      update cus_record c set
        c.information='重点客户' where c.cus_id in(
         select cus_id from(select ofi.cus_id,sum(decode(to_date(to_char(ofi.create_time,'yyyy-mm'),'yyyy-mm'),to_date(to_char(sysdate,'yyyy-mm'),'yyyy-mm'),(ofi.cp_fee+ofi.consignee_fee+ofi.sonderzug_price+ofi.cp_value_add_fee+ofi.cus_value_add_fee+ofi.cp_sonderzug_price),0)) profitFee from opr_fax_in ofi group by ofi.cus_id)
             where profitFee>=10000 and profitfee<50000
      );
      update cus_record c set
        c.information='常客' where c.cus_id in(
         select cus_id from(select ofi.cus_id,sum(decode(to_date(to_char(ofi.create_time,'yyyy-mm'),'yyyy-mm'),to_date(to_char(sysdate,'yyyy-mm'),'yyyy-mm'),(ofi.cp_fee+ofi.consignee_fee+ofi.sonderzug_price+ofi.cp_value_add_fee+ofi.cus_value_add_fee+ofi.cp_sonderzug_price),0)) profitFee from opr_fax_in ofi group by ofi.cus_id)
             where profitFee>=1000 and profitfee<10000
      );
      update cus_record c set
        c.information='散客' where c.cus_id in(
         select cus_id from(select ofi.cus_id,sum(decode(to_date(to_char(ofi.create_time,'yyyy-mm'),'yyyy-mm'),to_date(to_char(sysdate,'yyyy-mm'),'yyyy-mm'),(ofi.cp_fee+ofi.consignee_fee+ofi.sonderzug_price+ofi.cp_value_add_fee+ofi.cus_value_add_fee+ofi.cp_sonderzug_price),0)) profitFee from opr_fax_in ofi group by ofi.cus_id)
             where profitFee<1000
      );

      /*--客户盈利性分类*/
     update cus_record c
    set c.profit_type='A类'
    where c.cus_id in(
    select cus_id from(
    select cus_id,(income-costamount)/costamount profitRate
            from
                (select
                    t.cus_id,
                    nvl(sum(f.cost_amount),
                    0) costamount,
                    sum(t.payment_collection) paycoll,
                    nvl(sum(t.cp_value_add_fee),
                    0) cpvaladdfee,
                    nvl(sum(t.cus_value_add_fee),
                    0) convaladdfee,
                    nvl(sum(t.consignee_fee),
                    0)+nvl(sum(t.cp_fee),
                    0)+nvl(sum(case
                        when t.sonderzug=1 then t.cq_weight
                    end),
                    0)+nvl(sum(t.cp_value_add_fee),
                    0)+nvl(sum(t.cus_value_add_fee),
                    0)+nvl(sum(t.cp_sonderzug_price),
                    0) income
                from
                    opr_fax_in t,
                    fi_cost f
                where
                    t.d_no=f.d_no and to_date(to_char(t.create_time,'yyyy-mm'),'yyyy-mm')=to_date(to_char(sysdate,'yyyy-mm'),'yyyy-mm') and t.status=1
                    group by t.cus_id
                    )
     ) rate where profitrate>=0.8);

     update cus_record c
    set c.profit_type='B类'
    where c.cus_id in(
    select cus_id from(
    select cus_id,(income-costamount)/costamount profitRate
            from
                (select
                    t.cus_id,
                    nvl(sum(f.cost_amount),
                    0) costamount,
                    sum(t.payment_collection) paycoll,
                    nvl(sum(t.cp_value_add_fee),
                    0) cpvaladdfee,
                    nvl(sum(t.cus_value_add_fee),
                    0) convaladdfee,
                    nvl(sum(t.consignee_fee),
                    0)+nvl(sum(t.cp_fee),
                    0)+nvl(sum(case
                        when t.sonderzug=1 then t.cq_weight
                    end),
                    0)+nvl(sum(t.cp_value_add_fee),
                    0)+nvl(sum(t.cus_value_add_fee),
                    0)+nvl(sum(t.cp_sonderzug_price),
                    0) income
                from
                    opr_fax_in t,
                    fi_cost f
                where
                    t.d_no=f.d_no and to_date(to_char(t.create_time,'yyyy-mm'),'yyyy-mm')=to_date(to_char(sysdate,'yyyy-mm'),'yyyy-mm') and t.status=1
                    group by t.cus_id
                    )
     ) rate where profitrate>=0.05 and profitrate<0.8);

  update cus_record c
    set c.profit_type='C类'
    where c.cus_id in(
    select cus_id from(
    select cus_id,(income-costamount)/costamount profitRate
            from
                (select
                    t.cus_id,
                    nvl(sum(f.cost_amount),
                    0) costamount,
                    sum(t.payment_collection) paycoll,
                    nvl(sum(t.cp_value_add_fee),
                    0) cpvaladdfee,
                    nvl(sum(t.cus_value_add_fee),
                    0) convaladdfee,
                    nvl(sum(t.consignee_fee),
                    0)+nvl(sum(t.cp_fee),
                    0)+nvl(sum(case
                        when t.sonderzug=1 then t.cq_weight
                    end),
                    0)+nvl(sum(t.cp_value_add_fee),
                    0)+nvl(sum(t.cus_value_add_fee),
                    0)+nvl(sum(t.cp_sonderzug_price),
                    0) income
                from
                    opr_fax_in t,
                    fi_cost f
                where
                    t.d_no=f.d_no and to_date(to_char(t.create_time,'yyyy-mm'),'yyyy-mm')=to_date(to_char(sysdate,'yyyy-mm'),'yyyy-mm') and t.status=1
                    group by t.cus_id
                    )
     ) rate where profitrate<0.05);

--产品分析
begin
     select count(*) into pa_accountCount from product_analyse;
     if pa_accountCount=0 then
       endCountDate:=to_date('2010-1-1','yyyy-mm-dd');
     else
       select max(pa.count_time) into endCountDate from product_analyse pa;
     end if;
  insert into product_analyse
    (id, product_type, product_ticket, product_piece, product_weight, product_income, traffic_mode, count_time,depart_id,depart_name)
  select seq_product_analyse.nextval,t.productType,t.ticket,t.piece,t.weight,t.income,t.traffic_mode,sysdate,t.in_depart_id,t.in_depart from
  (
  select '新邦自提' productType,t.in_depart_id,t.in_depart,t.traffic_mode,count(*) ticket,sum(t.piece) piece,sum(t.cq_weight) weight,sum(t.cp_fee+t.consignee_fee+t.cp_value_add_fee+t.cus_value_add_fee+t.sonderzug_price+t.cp_sonderzug_price) income
   from opr_fax_in t
    where t.create_time>endCountDate and t.distribution_mode='新邦' and (t.take_mode='市内自提' or t.take_mode='机场自提') and t.status=1
     group by t.traffic_mode,t.in_depart_id,t.in_depart
  union
  select '新邦送货' productType,t.in_depart_id,t.in_depart,t.traffic_mode,count(*) ticket,sum(t.piece) piece,sum(t.cq_weight) weight,sum(t.cp_fee+t.consignee_fee+t.cp_value_add_fee+t.cus_value_add_fee+t.sonderzug_price+t.cp_sonderzug_price) income
   from opr_fax_in t
    where t.create_time>endCountDate and t.distribution_mode='新邦' and t.take_mode='市内送货' and t.status=1
     group by t.traffic_mode,t.in_depart_id,t.in_depart
  union
  select '中转送货' productType,t.in_depart_id,t.in_depart,t.traffic_mode,count(*) ticket,sum(t.piece) piece,sum(t.cq_weight) weight,sum(t.cp_fee+t.consignee_fee+t.cp_value_add_fee+t.cus_value_add_fee+t.sonderzug_price+t.cp_sonderzug_price) income
   from opr_fax_in t
    where t.create_time>endCountDate and t.distribution_mode='中转' and t.take_mode='市内送货' and t.status=1
     group by t.traffic_mode,t.in_depart_id,t.in_depart
  union
  select '专车送货' productType,t.in_depart_id,t.in_depart,t.traffic_mode,count(*) ticket,sum(t.piece) piece,sum(t.cq_weight) weight,sum(t.cp_fee+t.consignee_fee+t.cp_value_add_fee+t.cus_value_add_fee+t.sonderzug_price+t.cp_sonderzug_price) income
   from opr_fax_in t
    where t.create_time>endCountDate and t.sonderzug=1 and t.status=1
     group by t.traffic_mode,t.in_depart_id,t.in_depart
  union
  select '中转自提' productType,t.in_depart_id,t.in_depart,t.traffic_mode,count(*) ticket,sum(t.piece) piece,sum(t.cq_weight) weight,sum(t.cp_fee+t.consignee_fee+t.cp_value_add_fee+t.cus_value_add_fee+t.sonderzug_price+t.cp_sonderzug_price) income
   from opr_fax_in t
    where t.create_time>endCountDate and t.distribution_mode='中转' and t.take_mode='市内自提' and t.status=1
     group by t.traffic_mode,t.in_depart_id,t.in_depart
  union
  select '外发自提' productType,t.in_depart_id,t.in_depart,t.traffic_mode,count(*) ticket,sum(t.piece) piece,sum(t.cq_weight) weight,sum(t.cp_fee+t.consignee_fee+t.cp_value_add_fee+t.cus_value_add_fee+t.sonderzug_price+t.cp_sonderzug_price) income
   from opr_fax_in t
    where t.create_time>endCountDate and t.distribution_mode='外发' and t.take_mode='市内自提' and t.status=1
     group by t.traffic_mode,t.in_depart_id,t.in_depart
  union
  select '外发送货' productType,t.in_depart_id,t.in_depart,t.traffic_mode,count(*) ticket,sum(t.piece) piece,sum(t.cq_weight) weight,sum(t.cp_fee+t.consignee_fee+t.cp_value_add_fee+t.cus_value_add_fee+t.sonderzug_price+t.cp_sonderzug_price) income
   from opr_fax_in t
    where t.create_time>endCountDate and t.distribution_mode='外发' and t.take_mode='市内送货' and t.status=1
     group by t.traffic_mode,t.in_depart_id,t.in_depart
  ) t;
end;


begin
  select count(*) into accountCount from cus_analyse;
  if accountCount=0 then
     ca_endCountDate:=to_date('2010-1-1','yyyy-mm-dd');
  else
     select max(ca.count_time) into ca_endCountDate from cus_analyse ca;
  end if;
  insert into cus_analyse
    (id, cus_record_id, cus_name, cus_income, depart_id, depart_name, count_time,importance_level,cus_depart_code,cus_depart_name,cus_ticket,cus_weight)
  select seq_cus_analyse.nextval,t.id,t.cus_name,t.income,t.in_depart_id,t.in_depart,sysdate,t.importance_level,t.cus_depart_code,t.cus_depart_name,t.cus_ticket,t.cus_weight
  from
  (
  select cr.id,cr.cus_name,
  nvl(sum(t.cp_fee+t.consignee_fee+t.sonderzug_price+t.cp_value_add_fee+t.cus_value_add_fee+t.cp_sonderzug_price),0) income,
  t.in_depart_id,t.in_depart,t.cus_depart_code,t.cus_depart_name,cr.importance_level,
  count(t.d_no) cus_ticket,
  nvl(sum(t.cq_weight),0) cus_weight,
  sysdate
  from opr_fax_in t,cus_record cr
  where t.cus_id=cr.cus_id and t.create_time>ca_endCountDate and t.status=1
  group by
  cr.id,cr.cus_name,t.in_depart_id,t.in_depart,t.cus_depart_code,t.cus_depart_name,cr.importance_level
  ) t;
end;
commit;
end PRO_TIMING_CUS;
