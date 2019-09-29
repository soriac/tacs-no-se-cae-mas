import React from 'react';
import styled from 'styled-components';
import {Card, Spinner, Button} from '@blueprintjs/core';
import { Select } from "@blueprintjs/select";
import Repository from '../../../components/Repository';
import {User, Repo} from '../../../api/types';

const Root = styled.div`
    width: 100vw;
    max-height: 90vh;
    overflow-x: hidden;
    padding: 0.5rem;

    display: flex;
    justify-content: center;
`;

const Container = styled(Card)`
    & > * {
        margin-bottom: 1.5rem;
    }
    
    display: flex;
    flex-direction: column;
    align-items: center;
    
    overflow-y: auto;
`;

type Props = {
    users: User[]
    repos: Repo[]
    loading: boolean
    error: any | undefined
    compareFunction: (id1: string, id2: string) => Promise<void>
}

const renderUser = (user: User) => {
    return (
        <span>{user.email}</span>    
    )
}

const handleItemSelect = (user: User) => {
    return (
        <span>user.email</span>    
    )
}
const Layout: React.FC<Props> = ({users, loading, error, repos, compareFunction}) => {

    const UserSelect = Select.ofType<User>();

    return (
        <Root>
            <Container>
                <UserSelect items={users} itemRenderer={renderUser} onItemSelect={handleItemSelect}>
                     <Button text={'Select an option'} rightIcon="double-caret-vertical" />
                </UserSelect>
                <h2>My repos</h2>
                {
                    loading ?
                        <Spinner/>
                        : repos ?
                        repos.map(repo => <Repository repo={repo}/>)
                        : null
                }
            </Container>
        </Root>
    )
};

export default Layout;