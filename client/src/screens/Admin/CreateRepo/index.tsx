import React, {useEffect, useState} from 'react';
import {update} from 'ramda';
import {createAtGithub} from '../../../api';
import {AppToaster} from '../../../util/toaster';
import Layout from './Layout';
import {Repo, User, StatusPayload} from '../../../api/types';

const CreateRepo = () => {
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<any>(null);
    const [name, setName] = useState<any>(null);

    const handleSetName = (e: React.FormEvent<HTMLInputElement>) => {
        setName(e.currentTarget.value);
    }

    const handleCreate = async () => {
        setLoading(true);

        const result = await createAtGithub(name);

        setLoading(false);

        const {error}: StatusPayload = await result.json();
        if (result.status >= 300 && result.status < 500) {
            AppToaster.show({message: 'Repository has not been created.', intent: 'warning'});
            setError(error);
        } else if (result.status >= 500){
        	AppToaster.show({message: 'Internal server error', intent: 'warning'});
        }
        else {
            AppToaster.show({message: 'Repository has been created', intent: 'success'});
        }
    };

    return <Layout loading={loading} handleCreate={handleCreate} error={error} handleSetName={handleSetName}/>;
};

export default CreateRepo;