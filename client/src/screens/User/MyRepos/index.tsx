import React, {useEffect, useState} from 'react';
import {myRepos, removeFromFavorites} from '../../../api';
import Layout from './Layout';
import {Repo, SearchReposPayload} from '../../../api/types';
import {AppToaster} from '../../../util/toaster';

const MyRepos = () => {
    const [repos, setRepos] = useState<Repo[]>([]);
    const [loading, setLoading] = useState<boolean>(false);

    const handleRemoveFromFavorites = async (id: string) => {
        setLoading(true);

        const response = await removeFromFavorites(id);
        if (response.status !== 200) {
            AppToaster.show({message: 'Internal server error. Please try again.', intent: 'danger'});
        } else {
            AppToaster.show({message: 'Repo removed from favorites.', intent: 'success'});
        }

        setLoading(false);
        fetchRepos();
    };

    const fetchRepos = async () => {
        setLoading(true);
        const response = await myRepos();
        setLoading(false);

        if (response.status !== 200) {
            AppToaster.show({message: 'Error fetching repositories.', intent: 'danger'});
            return;
        }

        const {data}: SearchReposPayload = await response.json();
        setRepos(data);
    };

    useEffect(() => {
        fetchRepos();
    }, []);

    return <Layout loading={loading} repos={repos} removeFromFavorites={handleRemoveFromFavorites}/>;
};

export default MyRepos;