import React from 'react';
import styled from 'styled-components';
import {Card, Spinner} from '@blueprintjs/core';
import {User} from '../../../api/types';
import Table from './Table';

const Root = styled.div`
    width: 100vw;
    max-height: 90vh;
    overflow-x: hidden;
    padding: 0.5rem;

    display: flex;
    justify-content: center;
`;

const Container = styled(Card)`
    display: flex;
    flex-direction: column;
    align-items: center;
    
    overflow-y: auto;
    
    & > * {
        margin-bottom: 1.5rem;
    }
    
    & > .bp3-input-group {
      align-self: flex-end;    
    }
`;

type Props = {
    users: User[]
    loading: boolean
    error: any | undefined
    refetch: () => void | Promise<void>
}
const Layout: React.FC<Props> = ({users, loading, error, refetch}) => {
    return (
        <Root>
            <Container>
                <h2>Users</h2>
                {
                    loading ?
                        <Spinner/>
                        : users ?
                        <Table users={users} refetch={refetch}/>
                        : null

                }
            </Container>
        </Root>
    );
};

export default Layout;