import React, {useState} from 'react';
import styled from 'styled-components';
import RepositorySearchFilter from './index';
import {ToggleableFilter} from '../../screens/Admin/RepoSearch';
import {Button} from '@blueprintjs/core';

const Container = styled.div`
    max-width: 500px;
    
    display: flex;
    flex-direction: column;
    
    align-items: flex-start;
    
    & > :last-child {
        margin-top: 0.5rem;
        align-self: flex-end;
    }
`;

const FilterContainer = styled.div`
    display: grid;
    grid-template-columns: 1fr 2fr 6fr 3fr;
    grid-template-rows: auto;
    grid-column-gap: 0.5rem;
    grid-row-gap: 0.5rem;
    
    max-width: 500px;
    
    align-items: center;
    
    & > * {
      margin: 0;
    }
`;

type Props = {
    handleSearch: (filters: ToggleableFilter[]) => Promise<void>
}
const FilterGroup: React.FC<Props> = ({handleSearch}) => {
    const [sizeFilter, setSizeFilter] = useState<ToggleableFilter>({enabled: false, param: 'size', op: 'lt', value: 0});
    const [forksFilter, setForksFilter] = useState<ToggleableFilter>({
        enabled: false,
        param: 'forks',
        op: 'lt',
        value: 0
    });
    const [starsFilter, setStarsFilter] = useState<ToggleableFilter>({
        enabled: false,
        param: 'stars',
        op: 'lt',
        value: 0
    });
    const [topicsFilter, setTopicsFilter] = useState<ToggleableFilter>({
        enabled: false,
        param: 'topics',
        op: 'lt',
        value: 0
    });
    const [followersFilter, setFollowersFilter] = useState<ToggleableFilter>({
        enabled: false,
        param: 'followers',
        op: 'lt',
        value: 0
    });

    const filters = [sizeFilter, forksFilter, starsFilter, topicsFilter, followersFilter];
    const runSearch = () => handleSearch(filters);
    const searchEnabled = () => filters.some(filter => filter.enabled);

    return (
        <Container>
            <FilterContainer>
                <RepositorySearchFilter title='Size' filter={sizeFilter} setFilter={setSizeFilter}/>
                <RepositorySearchFilter title='Forks' filter={forksFilter} setFilter={setForksFilter}/>
                <RepositorySearchFilter title='Stars' filter={starsFilter} setFilter={setStarsFilter}/>
                <RepositorySearchFilter title='Topics' filter={topicsFilter} setFilter={setTopicsFilter}/>
                <RepositorySearchFilter title='Followers' filter={followersFilter} setFilter={setFollowersFilter}/>
            </FilterContainer>
            <Button intent='primary' onClick={runSearch} disabled={!searchEnabled()}>Search</Button>
        </Container>
    );
};

export default FilterGroup;