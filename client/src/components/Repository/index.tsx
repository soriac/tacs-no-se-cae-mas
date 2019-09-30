import React from 'react';
import styled from 'styled-components';
import {Button, Card} from '@blueprintjs/core';
import {Repo} from '../../api/types';

const Container = styled(Card)`
    width: 300px;

    & > *:not(button) {
        padding: 0;
        margin: 0;
    }

    small {
        display: inline-block;
        margin-bottom: 1.15rem;
    }
`;

const isPresent = (n?: number): boolean => n === 0 || !!n;

type Props = {
    repo: Repo
    addToFavorites?: (id: string) => Promise<void>
    removeFromFavorites?: (id: string) => Promise<void>
}
const Repository: React.FC<Props> = ({repo, addToFavorites, removeFromFavorites}) => {

    return (
        <Container elevation={2}>
            <h3 style={{wordWrap: 'break-word'}}>{repo.name}</h3>
            <p><small>{repo.id}</small></p>
            {repo.language && <p>Main Language: <strong>{repo.language}</strong></p>}
            {isPresent(repo.numStars) && <p>Stars: <strong>{repo.numStars}</strong></p>}
            {isPresent(repo.numForks) && <p>Forks: <strong>{repo.numForks}</strong></p>}
            {isPresent(repo.favCount) && <p>Fav count: <strong>{repo.favCount}</strong></p>}
            {
                addToFavorites ?
                    <Button intent='success' onClick={() => addToFavorites(repo.id)}>Add</Button>
                    : removeFromFavorites &&
                  <Button intent='danger' onClick={() => removeFromFavorites(repo.id)}>Remove</Button>
            }
        </Container>
    );

};

export default Repository;