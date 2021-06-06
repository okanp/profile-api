create extension if not exists cube;
create extension if not exists earthdistance;

DROP TABLE IF EXISTS public.profile CASCADE;

CREATE TABLE public.profile
(
    id uuid NOT NULL,
    birthday timestamp without time zone,
    elo_ranking integer,
    gender varchar(32),
    last_active_time timestamp without time zone,
    last_candidate_search_time timestamp without time zone,
    lat double precision,
    lon double precision,
    name varchar(128),
    CONSTRAINT profile_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.profile
    OWNER to postgres;


-- Table: public.preference

 DROP TABLE IF EXISTS public.preference;

CREATE TABLE public.preference
(
    id uuid NOT NULL,
    gender varchar(32),
    max_age integer,
    min_age integer,
    search_distance integer,
    profile_id uuid,
    CONSTRAINT preference_pkey PRIMARY KEY (id),
    CONSTRAINT fkt9nx3if254ip3siixlnehlpj7 FOREIGN KEY (profile_id)
        REFERENCES public.profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.preference
    OWNER to postgres;


-- Table: public.candidate

 DROP TABLE IF EXISTS public.candidate;

CREATE TABLE public.candidate
(
    id uuid NOT NULL,
    created_at timestamp without time zone,
    profile_a_response varchar(32),
    profile_a_response_date timestamp without time zone,
    profile_b_response varchar(32),
    profile_b_response_date timestamp without time zone,
    soul_mate_score double precision,
    status varchar(32),
    profile_a uuid NOT NULL,
    profile_b uuid NOT NULL,
    CONSTRAINT candidate_pkey PRIMARY KEY (id),
    CONSTRAINT fk2k7pvdvskb8pdk3s7jonm90g1 FOREIGN KEY (profile_a)
        REFERENCES public.profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fknvd3g68nu6rw50da8ttreqwav FOREIGN KEY (profile_b)
        REFERENCES public.profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.candidate
    OWNER to postgres;


-- Table: public.photos

 DROP TABLE IF EXISTS public.photos;

CREATE TABLE public.photos
(
    profile_id uuid NOT NULL,
    photos varchar(128),
    CONSTRAINT fkpj7ib45ombq79pv672mkkfdpn FOREIGN KEY (profile_id)
        REFERENCES public.profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.photos
    OWNER to postgres;