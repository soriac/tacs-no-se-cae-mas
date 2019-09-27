import React from 'react';

import Layout from './layout';
import {signup} from '../../api';
import {AppToaster} from '../../util/toaster';
import {useHistory} from 'react-router';

const Signup: React.FC = () => {
    const history = useHistory();

    const handleSignup = async (email: string, password: string) => {
        const response = await signup(email, password);

        switch (response.status) {
            case 201:
                AppToaster.show({message: 'Account created successfully! Please login now.', intent: 'success'});
                history.push('/');
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
        <Layout signup={handleSignup}/>
    );
};

export default Signup;