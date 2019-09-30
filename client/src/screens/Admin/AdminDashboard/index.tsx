import React, {useEffect, useState} from 'react';
import {reposCount} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';

const AdminDashboard = () => {
    const [date, setDate] = React.useState<Date>(new Date());
    const [systemReposCount, setReposCount] = useState<number>();
    const [loading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        const search = async () => {
            setLoading(true);

            const result = await reposCount(date);

            setLoading(false);

            if (result.status !== 200) {
                AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
            } else {
                const {data} = await result.json();
                console.log(data);
                setReposCount(data);
            }
        };

        search();
    }, [date]);

    return <Layout
        loading={loading}
        repoCount={systemReposCount}
        date={date}
        setDate={setDate}/>;
};

export default AdminDashboard;