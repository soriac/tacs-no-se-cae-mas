import React from 'react';
import styled from 'styled-components';
import {Spinner} from '@blueprintjs/core';
import {User} from '../../../api/types';
import Table from './Table';
import {ContentContainer} from '../../../components/ContentContainer';
import {Root} from '../../../components/Root';

const Container = styled(ContentContainer)`
    & > .bp3-input-group {
      align-self: flex-end;    
    }
`;

type Props = {
    users: User[]
    loading: boolean
    refetch: () => void | Promise<void>
}
const Layout: React.FC<Props> = ({users, loading, refetch}) => {
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