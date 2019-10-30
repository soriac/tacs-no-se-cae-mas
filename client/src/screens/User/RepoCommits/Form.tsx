import React from 'react';
import {Field, Form as FormikForm, Formik, FormikActions} from 'formik';
import * as yup from 'yup';
import {InputGroup} from 'formik-blueprint';
import {Button} from '@blueprintjs/core';
import {AppToaster} from '../../../util/toaster';
import styled from 'styled-components';

const FormContainer = styled.div`
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    
    *:not(:last-child) {
        margin-right: 0.5rem;
    }
`;

export type FormAttributes = {
    author: string
    name: string
}

const initialValues: FormAttributes = {
    author: 'soriac',
    name: 'tacs-no-se-cae-mas'
};

const REQ_MSG = 'Campo requerido';
const schema = yup.object().shape({
    author: yup.string().required(REQ_MSG),
    name: yup.string().required(REQ_MSG)
});


type Props = {
    getCommits: (author: string, name: string) => Promise<void>
}
const Form = ({getCommits}: Props) => {
    const onSubmit = async (values: FormAttributes, actions: FormikActions<FormAttributes>) => {
        actions.setSubmitting(true);
        try {
            await getCommits(values.author, values.name);
        } catch (e) {
            AppToaster.show({message: e, intent: 'danger'});
        } finally {
            actions.setSubmitting(false);
        }
    };

    return (
        <Formik
            isInitialValid={true}
            initialValues={initialValues}
            validationSchema={schema}
            onSubmit={onSubmit}
            render={({isSubmitting, isValid}) => (
                <FormikForm>
                    <FormContainer>
                        <Field
                            name="author"
                            placeholder="Nombre Autor"
                            component={InputGroup}
                            type="text"/>
                        <Field
                            name="name"
                            placeholder="Nombre Repo"
                            component={InputGroup}
                            type="text"/>
                        <Button type="submit" disabled={isSubmitting || !isValid}>Enviar</Button>
                    </FormContainer>
                </FormikForm>
            )}
        />
    );
};

export default Form;