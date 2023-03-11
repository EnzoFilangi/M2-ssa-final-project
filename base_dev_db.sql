--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1 (Debian 15.1-1.pgdg110+1)
-- Dumped by pg_dump version 15.1 (Debian 15.1-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: companies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.companies (
    id uuid NOT NULL,
    address character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.companies OWNER TO postgres;

--
-- Name: internships; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.internships (
    id uuid NOT NULL,
    cahierdescharges boolean,
    end_date date NOT NULL,
    ficheevaluationentreprise boolean,
    fichevisite boolean,
    notecom real,
    notetech real,
    rapportrendu boolean,
    sondageweb boolean,
    soutenance boolean,
    start_date date NOT NULL,
    visitefaite boolean,
    visiteplanifiee boolean,
    company_id uuid NOT NULL,
    student_id uuid NOT NULL
);


ALTER TABLE public.internships OWNER TO postgres;

--
-- Name: sessions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sessions (
    id uuid NOT NULL,
    expiry_date timestamp(6) without time zone NOT NULL,
    user_id uuid NOT NULL
);


ALTER TABLE public.sessions OWNER TO postgres;

--
-- Name: students; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.students (
    id uuid NOT NULL,
    first_name character varying(255) NOT NULL,
    student_group character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    tutor_id uuid NOT NULL
);


ALTER TABLE public.students OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: companies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.companies (id, address, name) FROM stdin;
9370e31d-6e0c-498f-902e-eceaba6d12cf	131 Av. Charles de Gaulle Hall A, 92200 Neuilly-sur-Seine	Carbon IT
\.


--
-- Data for Name: internships; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.internships (id, cahierdescharges, end_date, ficheevaluationentreprise, fichevisite, notecom, notetech, rapportrendu, sondageweb, soutenance, start_date, visitefaite, visiteplanifiee, company_id, student_id) FROM stdin;
b265602a-a79f-4edb-9d84-c96f56116952	t	2023-09-30	f	t	0	0	f	t	f	2023-04-03	f	f	9370e31d-6e0c-498f-902e-eceaba6d12cf	31d83d0d-c4cc-4766-bf81-0015f17c95cb
\.


--
-- Data for Name: sessions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sessions (id, expiry_date, user_id) FROM stdin;
55d182ca-defd-488f-be25-9b8cc402f242	2023-03-12 16:02:15.393	1885a7ba-b05f-445d-a677-00643baa8c34
\.


--
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.students (id, first_name, student_group, last_name, tutor_id) FROM stdin;
31d83d0d-c4cc-4766-bf81-0015f17c95cb	Samuel	M2SE1	Bader	42faa541-35be-468e-a2c1-7226496ce5e5
e0599fbf-0cdc-448e-93c7-466ed3d8663f	Enzo	M2SE1	Filangi	42faa541-35be-468e-a2c1-7226496ce5e5
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, first_name, last_name, password, username) FROM stdin;
1885a7ba-b05f-445d-a677-00643baa8c34	Enzo	Filangi	1000:41cf02b57c4d12a5f0edffd6ecbce32811e1d42a91d7caaf:23f6145b039c22f6550e1262b86d64836830663c2002362b	ef@efrei.net
42faa541-35be-468e-a2c1-7226496ce5e5	Jacques	Augustin	1000:11fc456bbeed44e98b255a9a02001b8f996b85f6929e67f9:d87e618ab8f44e0d3d2bf950b76f99f28488a14192ce82a6	ja@efrei.fr
\.


--
-- Name: companies companies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.companies
    ADD CONSTRAINT companies_pkey PRIMARY KEY (id);


--
-- Name: internships internships_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.internships
    ADD CONSTRAINT internships_pkey PRIMARY KEY (id);


--
-- Name: sessions sessions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT sessions_pkey PRIMARY KEY (id);


--
-- Name: students students_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_pkey PRIMARY KEY (id);


--
-- Name: users uk_dc4eq7plr20fdhq528twsak1b; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_dc4eq7plr20fdhq528twsak1b UNIQUE (username);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: internships fk5t2gdptjbiv1rie7gl77yj6x0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.internships
    ADD CONSTRAINT fk5t2gdptjbiv1rie7gl77yj6x0 FOREIGN KEY (company_id) REFERENCES public.companies(id);


--
-- Name: students fkkci3l1l85kxm8rq2auyucol6s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT fkkci3l1l85kxm8rq2auyucol6s FOREIGN KEY (tutor_id) REFERENCES public.users(id);


--
-- Name: internships fkoxdvo32lt3lo3cop4nanjeau1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.internships
    ADD CONSTRAINT fkoxdvo32lt3lo3cop4nanjeau1 FOREIGN KEY (student_id) REFERENCES public.students(id);


--
-- PostgreSQL database dump complete
--

