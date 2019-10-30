import React, {useState} from 'react';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';
import {getCommitsForRepo} from '../../../api';
import {Commit} from '../../../api/types';

const Commits = () => {
    const [commits, setCommits] = useState<Commit[]>([]);
    const [loading, setLoading] = useState<boolean>(false);

    const getCommits = async (author: string, name: string) => {
        setLoading(true);
        const result = await getCommitsForRepo(author, name);

        if (result.status !== 200) {
            AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
        } else {
            const response = await result.json();
            if (response.error) {
                AppToaster.show({message: response.error, intent: 'danger'});
            } else {
                const commits: Commit[] = response.data;
                setCommits(commits);
            }
        }

        setLoading(false);
    };

    return <Layout commits={commits ? commits : []} loading={loading} getCommits={getCommits}/>;
};

export default Commits;