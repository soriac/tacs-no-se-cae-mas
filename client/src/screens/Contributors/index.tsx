import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router';
import {getContributorsOfRepo} from '../../api';
import Layout from './Layout';
import {ContributorGithub} from '../../api/types';
import {AppToaster} from "../../util/toaster";

const Contributors = () => {
    const [contributors, setContributors] = useState<ContributorGithub[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const {id} = useParams();

    const getContributors = async (id: string) => {
        setLoading(true);

        const result = await getContributorsOfRepo(id);

        if (result.status !== 200) {
            AppToaster.show({message: 'Internal server error. Please try again.', intent: 'warning'});
        } else {
            const contributors: ContributorGithub[] = (await result.json()).data;
            setContributors(contributors);
        }

        setLoading(false);
    };

    useEffect(() => {
        if (id) getContributors(id);
    }, [id]);

    return <Layout loading={loading} contributors={contributors}/>;
};

export default Contributors;