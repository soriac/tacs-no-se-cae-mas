import React, {useState} from 'react';
import {searchRepos} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';
import {Filter, Repo, SearchReposPayload} from '../../../api/types';

export type ToggleableFilter = Filter & { enabled: boolean }
const RepoSearch = () => {
    const [repos, setRepos] = useState<Repo[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<any>(null);

    const handleSearch = async (filters: ToggleableFilter[]) => {
        setLoading(true);

        const enabledFilters = filters.filter(filter => filter.enabled);
        const result = await searchRepos(enabledFilters);

        setLoading(false);

        const {data, error}: SearchReposPayload = await result.json();
        if (result.status !== 200) {
            AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
            setError(error);
        } else {
            setRepos(data);
        }
    };

    return <Layout error={error} loading={loading} repos={repos} handleSearch={handleSearch}/>;
};

export default RepoSearch;