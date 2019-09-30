import React from 'react';
import styled from 'styled-components';
import {Card} from '@blueprintjs/core';
import {User} from '../../api/types';

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
    user: User
    favCount?: boolean | undefined
}
const UserComponent: React.FC<Props> = ({user, favCount}) => {

    return (
        <Container elevation={1}>
            <p>User: <strong>{user.email}</strong></p>
            <p>Id: <strong>{user.id}</strong></p>
            <p>Last login: <strong>{new Date(user.lastLogin).toISOString().replace('T', ' ').replace('Z', '')}</strong></p>
            {favCount && user.role === 'USER' ?
                <p>Favorite repos count: <strong>{user.favRepos.length}</strong></p> : ''}
        </Container>
    );

};

export default UserComponent;