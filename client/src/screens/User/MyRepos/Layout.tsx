import React from 'react';
import {Spinner} from '@blueprintjs/core';
import Repository from '../../../components/Repository';
import {Repo} from '../../../api/types';
import {Root} from '../../../components/Root';
import {ContentContainer} from '../../../components/ContentContainer';

type Props = {
    repos: Repo[]
    loading: boolean
    removeFromFavorites?: (id: string) => Promise<void>
}
const Layout: React.FC<Props> = ({repos, loading, removeFromFavorites}) => {

    return (
        <Root>
            <ContentContainer>
                <h2>My repos</h2>
                {
                    loading ?
                        <Spinner/>
                        : repos &&
                        repos.map(repo => <Repository repo={repo} removeFromFavorites={removeFromFavorites}/>)
                }
            </ContentContainer>
        </Root>
    )
};

export default Layout;