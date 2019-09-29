import React from 'react';
import styled from 'styled-components';
import {Button, Card} from '@blueprintjs/core';
import {Repo} from '../../api/types';

const Container = styled(Card)`
    width: 300px;

    * {
        padding: 0;
        margin: 0;
    }

    small {
        display: inline-block;
        margin-bottom: 1.15rem;
    }
`;

type Props = {
    repo: Repo
    favCount?: boolean | undefined
    addToFavorites?: (id: string) => Promise<void>
    removeFromFavorites?: (id: string) => Promise<void>
}
const Repository: React.FC<Props> = ({repo, favCount, addToFavorites, removeFromFavorites}) => {

    return (
        <Container elevation={1}>
            <h3>{repo.name}</h3>
            <p><small>{repo.id}</small></p>
            { repo.language ? <p>Main Language: <strong>{repo.language}</strong></p> : null }
            { repo.numStars ? <p>Stars: <strong>{repo.numStars}</strong></p> : null }
            { repo.numForks ? <p>Forks: <strong>{repo.numForks}</strong></p> : null }
            { favCount ? <p>Fav count: <strong>{repo.favCount}</strong></p> : null }
            {
                addToFavorites ?
                    <Button intent='primary' onClick={() => addToFavorites(repo.id)}>Agregar a favoritos</Button>
                    : removeFromFavorites ?
                    <Button intent='primary' onClick={() => removeFromFavorites(repo.id)}>Eliminar de favoritos</Button>
                    : null
            }
        </Container>
    );

};

export default Repository;