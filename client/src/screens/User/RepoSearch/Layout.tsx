import React from 'react';
import {Spinner} from '@blueprintjs/core';
import Repository from '../../../components/Repository';
import FilterGroup from '../../../components/RepositorySearchFilter/FilterGroup';
import {ToggleableFilter} from './index';
import {Repo} from '../../../api/types';
import {Root} from '../../../components/Root';
import {ContentContainer} from '../../../components/ContentContainer';

type Props = {
    repos: Repo[]
    loading: boolean
    error: any | undefined
    handleSearch: (filter: ToggleableFilter[]) => Promise<void>
    addToFavorites?: (id: string) => Promise<void>
    myRepos: string[]
}
const Layout: React.FC<Props> = ({repos, loading, handleSearch, addToFavorites, myRepos}) => {

    return (
        <Root>
            <ContentContainer>
                <h2>Repository Search</h2>
                <FilterGroup handleSearch={handleSearch}/>
                {
                    loading ?
                        <Spinner/>
                        : repos ?
                            repos.map(repo => {
                                if (myRepos.includes(repo.id.toString())) {
                                    return <Repository repo={repo}/>
                                }
                                else {
                                    return <Repository repo={repo} addToFavorites={addToFavorites}/>   
                                }
                            })
                        : null
                }
            </ContentContainer>
        </Root>
    )
};

export default Layout;