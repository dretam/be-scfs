CREATE TABLE IF NOT EXISTS public.role_children
(
    code VARCHAR(255) NOT NULL,
    parent_code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    icon VARCHAR(255),

    created_by UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID,
    updated_at TIMESTAMPTZ,
    deleted_by UUID,
    deleted_at TIMESTAMPTZ,

    CONSTRAINT pk_role_children PRIMARY KEY (code),

    CONSTRAINT fk_role_parent
    FOREIGN KEY (parent_code)
    REFERENCES public.roles (code)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,

    CONSTRAINT chk_no_self_reference
    CHECK (code <> parent_code)
);