create table tbl_product_insert_history (
	id serial primary key,
	insert_type varchar(255),
	start_at timestamptz,
	end_at timestamptz
);

create table tbl_product (
	id bigint default nextval('tbl_product_id_seq') primary key,
	insert_history_id bigint,
	title varchar(255),
	foreign key (insert_history_id) references tbl_product_insert_history (id)
);