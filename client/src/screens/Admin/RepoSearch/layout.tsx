import React from 'react';
import styled from 'styled-components';
import {Filter, Repo} from '../../../api';
import {Spinner} from '@blueprintjs/core';

const Root = styled.div`
  width: 100vw;
  overflow-x: hidden;
  padding: 0.5rem;
  
  display: flex;
  justify-content: center;
`;

const Container = styled.div`
  width: 90%;
  height: auto;
  
  box-shadow: 0 0 2px rgba(0, 0, 0, 0.25);
  border-radius: 5px;
  padding: 0.5rem;
`;

type Props = {
    repos: Repo[]
    loading: boolean
    error: any | undefined
    doSearch: (filter: Filter[]) => Promise<void>
}
const Layout: React.FC<Props> = ({repos, loading, error, doSearch}) => {

    return (
        <Root>
            <Container>
                <button onClick={() => doSearch([])}>Test</button>
                {
                    loading ?
                        <Spinner/>
                        : repos ?
                        repos.map(repo => (
                            <>
                                <h1>{repo.name}</h1>
                                <small>{repo.id}</small>
                                <p>{repo.language}</p>
                            </>
                        ))
                        : null

                }
            </Container>
        </Root>
    );
};

export default Layout;