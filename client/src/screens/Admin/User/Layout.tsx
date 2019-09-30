import React from 'react';
import styled from 'styled-components';
import {Card, Spinner} from '@blueprintjs/core';
import UserComponent from '../../../components/User';
import {User} from '../../../api/types';

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
    user: User | undefined
    loading: boolean
    error: any | undefined
}
const Layout: React.FC<Props> = ({user, loading, error}) => {

    return (
        <Root>
            <Container>
                <h2>Show user</h2>
                {
                loading ? 
                    <Spinner/> 
                : user ? 
                    <UserComponent user={user} favCount/> 
                : ''
                }
            </Container>
        </Root>
    )
};

export default Layout;