import React from 'react';
import {Field, Form as FormikForm, Formik, FormikActions} from 'formik';
import * as yup from 'yup';
import {InputGroup} from 'formik-blueprint';
import {Button} from '@blueprintjs/core';

export type FormAttributes = {
    email: string;
    password: string;
}

const initialValues: FormAttributes = {
    email: '',
    password: ''
};

const REQ_MSG = 'Campo requerido';
const schema = yup.object().shape({
    email: yup.string().required(REQ_MSG),
    password: yup.string().required(REQ_MSG)
});


type Props = {
    signup: (email: string, password: string) => Promise<void>
}

const Form = ({signup}: Props) => {
    const onSubmit = async ({email, password}: FormAttributes, actions: FormikActions<FormAttributes>) => {
        actions.setSubmitting(true);
        await signup(email, password);
        actions.setSubmitting(false);
    };

    return (
        <Formik
            initialValues={initialValues}
            validationSchema={schema}
            onSubmit={onSubmit}
            render={({isSubmitting, isValid}) => (
                <FormikForm>
                    <Field
                        name="email"
                        placeholder="Email"
                        component={InputGroup}
                        type="text"/>
                    <Field
                        name="password"
                        placeholder="Contraseña"
                        component={InputGroup}
                        type="password"/>
                    <Button type="submit" disabled={isSubmitting || !isValid}>Enviar</Button>
                </FormikForm>
            )}
        />
    );
};

export default Form;