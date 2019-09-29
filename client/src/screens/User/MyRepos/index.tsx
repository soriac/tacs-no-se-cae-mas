import React, {useEffect, useState} from 'react';
import {myRepos} from '../../../api';
import {removeFromFavorites} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';
import {Repo, SearchReposPayload} from '../../../api/types';

const MyRepos = () => {
    const [repos, setRepos] = useState<Repo[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<any>(null);

    const removeFromFavoritesFunction = async (id: string) => {
        setLoading(true);
        await removeFromFavorites(id);
        setLoading(false);
        fetchRepos();
    }

    const fetchRepos = async () => {
        setLoading(true);
        const result = await myRepos();
        setLoading(false);
        const {data}: SearchReposPayload = await result.json();
        setRepos(data);
    }

    useEffect(() => {
        fetchRepos();
    }, []);

    return <Layout error={error} loading={loading} repos={repos} removeFromFavorites={removeFromFavoritesFunction}/>;
};

export default MyRepos;