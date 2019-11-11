import React from 'react';
import styled from 'styled-components';
import {Spinner} from '@blueprintjs/core';
import Repository from '../../../components/Repository';
import {Repo, User} from '../../../api/types';
import UserSelect from './UserSelect';
import {ContentContainer} from '../../../components/ContentContainer';
import {Root} from '../../../components/Root';
import UserComponent from '../../../components/User';

const ComparatorContainer = styled.div`
    display: flex;
    flex-direction: column;
    flex-wrap: nowrap;
    
    & > :not(:last-child) {
        margin-right: 0.5rem;
    }
`;

type Props = {
    users: User[]
    selectedUsers: [User[], User[]]
    repos: Repo[]
    languages: String[]
    loadingUsers: boolean
    loadingComparison: boolean
    setUser: (n: number) => (user: User) => void
}

const Layout: React.FC<Props> = ({users, loadingUsers, selectedUsers, loadingComparison, repos, languages, setUser}) => {
    return (
        <Root>
            <ContentContainer>
                <h2>Compare favorite repositories and languages</h2>
                {
                    loadingUsers ?
                        <Spinner/>
                        :
                        <>
                            <ComparatorContainer>
                                <h3>Lista 1</h3>
                                {selectedUsers[0].map(user => <UserComponent user={user}/>)}
                                <UserSelect users={users} setUser={setUser(0)} selectedUser={selectedUsers[0]}/>
                            </ComparatorContainer>
                            <ComparatorContainer>
                                <h3>Lista 2</h3>
                                {selectedUsers[1].map(user => <UserComponent user={user}/>)}
                                <UserSelect users={users} setUser={setUser(1)} selectedUser={selectedUsers[1]}/>
                            </ComparatorContainer>
                            {loadingComparison ? <Spinner/> : <p></p>}
                            <h4 hidden={loadingComparison}>Shared repos</h4>
                            {
                                repos.map(repo => <Repository repo={repo}/>)
                            }
                            <h4 hidden={loadingComparison}>Shared languages</h4>
                            <p hidden={loadingComparison}>{languages.join(", ")}</p>
                        </>
                }
            </ContentContainer>
        </Root>
    )
};

export default Layout;