import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router';
import {oneUser} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';
import {User} from '../../../api/types';

const UserScreen = () => {
    const [user, setUser] = useState<User>();
    const [loading, setLoading] = useState<boolean>(false);
    const { id } = useParams();

    const getUser = async (id: string) => {
        setLoading(true);
        const result = await oneUser(id);

        if (result.status !== 200) {
            AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
        } else {
            const user: User = (await result.json()).data;
            setUser(user);
        }

        setLoading(false);
    };

    useEffect(() => {
        if (id) getUser(id);
    }, [id]);

    return <Layout loading={loading} user={user}/>;
};

export default UserScreen;