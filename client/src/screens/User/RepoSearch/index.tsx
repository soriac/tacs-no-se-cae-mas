import React, {useEffect, useState} from 'react';
import {searchRepos, myRepos as myReposApi} from '../../../api';
import {addToFavorites} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';
import {Filter, Repo, SearchReposPayload} from '../../../api/types';

export type ToggleableFilter = Filter & { enabled: boolean }
const UserRepoSearch = () => {
    const [repos, setRepos] = useState<Repo[]>([]);
    const [myRepos, setMyRepos] = useState<string[]>([]);
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

    const addToFavoritesFunction = async (id: string) => {
        setLoading(true);
        await addToFavorites(id);
        const myReposAux = myRepos.concat(id.toString());
        setMyRepos(myReposAux);
        setLoading(false);
    }

    const fetchRepos = async () => {
        setLoading(true);
        const result = await myReposApi();
        setLoading(false);
        const {data}: SearchReposPayload = await result.json();
        setMyRepos(data.map(repo => repo.id));
    }

    useEffect(() => {
        fetchRepos();
    }, []);

    return <Layout error={error} loading={loading} repos={repos} handleSearch={handleSearch} addToFavorites={addToFavoritesFunction} myRepos={myRepos}/>;
};

export default UserRepoSearch;