import React, {useEffect, useState} from 'react';
import {me} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';
import {User} from '../../../api/types';

const UserDashboard = () => {
    const [user, setUser] = useState<User>();
    const [loading, setLoading] = useState<boolean>(false);

    const getUser = async () => {
        setLoading(true);

        const result = await me();

        if (result.status !== 200) {
            AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
        } else {
            const user: User = (await result.json()).data;
            setUser(user);
        }

        setLoading(false);
    };

    useEffect(() => {
        getUser();
    }, []);

    return <Layout loading={loading} user={user}/>;
};

export default UserDashboard;