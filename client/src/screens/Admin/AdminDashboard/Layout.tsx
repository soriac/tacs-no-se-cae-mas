import React from 'react';
import styled from 'styled-components';
import {Card, Spinner, Button} from '@blueprintjs/core';
import {DatePicker} from '@blueprintjs/datetime';
import Repository from '../../../components/Repository';
import {Repo} from '../../../api/types';

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
    reposCount: number | undefined
    loading: boolean
    error?: any | undefined
    handleSearch: (since: string) => Promise<void>
}
const Layout: React.FC<Props> = ({reposCount, loading, error, handleSearch}) => {

    return (
        <Root>
            <Container>
                <Container>
                    <DatePicker/>
                    <Button intent='primary' icon='search'>Search</Button>
                </Container>
                {
                    loading ?
                        <Spinner/>
                        : reposCount ? 
                        <p>Repos in sistem since: <strong>reposCount</strong></p>
                        : null
                }
            </Container>
        </Root>
    )
};

export default Layout;