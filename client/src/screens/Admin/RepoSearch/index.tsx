import React, {useState} from 'react';
import {Filter, Repo, searchRepos} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './layout';

const RepoSearch = () => {
    const [repos, setRepos] = useState<Repo[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<any>(null);

    const doSearch = async (filters: Filter[]) => {
        setLoading(true);

        const result = await searchRepos(filters);

        setLoading(false);

        const {data, error} = await result.json();
        if (result.status !== 200) {
            AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
            setError(error);
        } else {
            setRepos(data);
        }
    };

    return <Layout error={error} loading={loading} repos={repos} doSearch={doSearch}/>;
};

export default RepoSearch;