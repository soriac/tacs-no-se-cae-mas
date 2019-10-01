import React from 'react';
import {RouteComponentProps} from 'react-router-dom';
import {useDispatch} from 'react-redux';

import Layout from './layout';
import {login, me} from '../../api';
import {AppToaster} from '../../util/toaster';
import {setToken, setUser} from '../../state/auth/actions';
import {MePayload} from '../../api/types';

type Props = RouteComponentProps;
const Login = (props: Props) => {

    const dispatch = useDispatch();

    const getMyInfo = async () => {
        const response = await me();

        if (response.status === 200) {
            const {data}: MePayload = await response.json();
            dispatch(setUser(data.email, data.role));
            AppToaster.show({message: 'Successfully logged in!', intent: 'success'});
            props.history.push('/');
        } else {
            AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
        }
    };

    const handleLogin = async (email: string, password: string) => {
        const response = await login(email, password);

        switch (response.status) {
            case 201:
                const token = (await response.json()).data;
                dispatch(setToken(token));
                await getMyInfo();
                break;
            case 404:
                AppToaster.show({message: 'Invalid username or password. Please try again.', intent: 'danger'});
                break;
            default:
                AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
                break;
        }

    };

    return (
        <Layout login={handleLogin}/>
    );
};

export default Login;