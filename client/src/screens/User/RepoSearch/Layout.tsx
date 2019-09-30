import React from 'react';
import styled from 'styled-components';
import {Card, Spinner} from '@blueprintjs/core';
import Repository from '../../../components/Repository';
import FilterGroup from '../../../components/RepositorySearchFilter/FilterGroup';
import {ToggleableFilter} from './index';
import {Repo} from '../../../api/types';

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
    repos: Repo[]
    loading: boolean
    error: any | undefined
    handleSearch: (filter: ToggleableFilter[]) => Promise<void>
    addToFavorites?: (id: string) => Promise<void>
    myRepos: string[]
}
const Layout: React.FC<Props> = ({repos, loading, error, handleSearch, addToFavorites, myRepos}) => {

    return (
        <Root>
            <Container>
                <h2>Repository Search</h2>
                <FilterGroup handleSearch={handleSearch}/>
                {
                    loading ?
                        <Spinner/>
                        : repos ?
                            repos.map(repo => {
                                console.log(repo.id)
                                console.log(repo)
                                console.log(myRepos)
                                if (myRepos.includes(repo.id.toString())) {
                                    return <Repository repo={repo}/>
                                }
                                else {
                                    return <Repository repo={repo} addToFavorites={addToFavorites}/>   
                                }
                            })
                        : null
                }
            </Container>
        </Root>
    )
};

export default Layout;