-- ======================
-- USERS
-- ======================
ALTER TABLE public.users
    ADD COLUMN role_children_code character varying(255);

ALTER TABLE public.users
    ADD CONSTRAINT fk_user_role_children
        FOREIGN KEY (role_children_code)
            REFERENCES public.role_children (code)
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
    NOT VALID;