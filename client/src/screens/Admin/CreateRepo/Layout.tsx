import React from 'react';
import styled from 'styled-components';
import {Spinner, InputGroup, Button} from '@blueprintjs/core';
import Repository from '../../../components/Repository';
import {Repo, User} from '../../../api/types';
import {ContentContainer} from '../../../components/ContentContainer';
import {Root} from '../../../components/Root';
import UserComponent from "../../../components/User";

const ComparatorContainer = styled.div`
    display: flex;
    flex-direction: column;
    flex-wrap: nowrap;
    
    & > :not(:last-child) {
        margin-right: 0.5rem;
    }
`;

type Props = {
    loading: boolean
    error: any | undefined
    handleCreate: () => Promise<void>
    handleSetName: (e: React.FormEvent<HTMLInputElement>) => void
}

const Layout: React.FC<Props> = ({loading, error, handleCreate, handleSetName}) => {
    return (
        <Root>
            <ContentContainer>
                <h2>Create repository at Github</h2>
                {
                    loading ?
                        <Spinner/>
                        :
                        <>
                            <InputGroup onChange={handleSetName} />
                            <Button onClick={handleCreate} icon="add">Create</Button>
                        </>
                }
            </ContentContainer>
        </Root>
    )
};

export default Layout;