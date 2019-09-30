import React, {useState} from 'react';
import {reposCount} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';

const AdminDashboard = () => {
    const [systemReposCount, setReposCount] = useState<number>();
    const [loading, setLoading] = useState<boolean>(false);

    const handleSearch = async (since: string) => {
        setLoading(true);

        const result = await reposCount(since);

        setLoading(false);

        if (result.status !== 200) {
            AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
        } else {
            console.log(result)
            setReposCount(5);
        }
    };

    return <Layout loading={loading} reposCount={systemReposCount} handleSearch={handleSearch}/>;
};

export default AdminDashboard;