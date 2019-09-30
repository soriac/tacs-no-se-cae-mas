import React from 'react';
import styled from 'styled-components';
import {Spinner} from '@blueprintjs/core';
import Repository from '../../../components/Repository';
import {Repo, User} from '../../../api/types';
import UserSelect from './UserSelect';
import {ContentContainer} from '../../../components/ContentContainer';
import {Root} from '../../../components/Root';

const ComparatorContainer = styled.div`
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    
    & > :not(:last-child) {
        margin-right: 0.5rem;
    }
`;

type Props = {
    users: User[]
    selectedUsers: [User?, User?]
    repos: Repo[]
    loadingUsers: boolean
    loadingComparison: boolean
    setUser: (n: number) => (user: User) => void
}

const Layout: React.FC<Props> = ({users, loadingUsers, selectedUsers, loadingComparison, repos, setUser}) => {
    return (
        <Root>
            <ContentContainer>
                <h2>Compare favorite repositories</h2>
                {
                    loadingUsers ?
                        <Spinner/>
                        :
                        <>
                            <ComparatorContainer>
                                <UserSelect users={users} setUser={setUser(0)} selectedUser={selectedUsers[0]}/>
                                <UserSelect users={users} setUser={setUser(1)} selectedUser={selectedUsers[1]}/>
                            </ComparatorContainer>
                            {
                                loadingComparison ?
                                    <Spinner/>
                                    :
                                    repos.map(repo => <Repository repo={repo}/>)
                            }
                        </>
                }
            </ContentContainer>
        </Root>
    )
};

export default Layout;