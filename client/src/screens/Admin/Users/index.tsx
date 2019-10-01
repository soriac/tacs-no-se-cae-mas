import React, {useEffect, useState} from 'react';
import {allUsers} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';
import {User} from '../../../api/types';

const Users = () => {
    const [users, setUsers] = useState<User[]>([]);
    const [loading, setLoading] = useState<boolean>(false);

    const getUsers = async () => {
        setLoading(true);

        const result = await allUsers();

        if (result.status !== 200) {
            AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
        } else {
            const users: User[] = (await result.json()).data;
            setUsers(users);
        }

        setLoading(false);
    };

    useEffect(() => {
        getUsers();
    }, []);

    return <Layout loading={loading} users={users} refetch={getUsers}/>;
};

export default Users;