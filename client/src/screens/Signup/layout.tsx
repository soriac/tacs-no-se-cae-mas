import React from 'react';
import styled from 'styled-components';
import bg from './bg.jpg';
import Form from './form';

const Root = styled.div`
    width: 100vw;
    height: 100vh;
    overflow: hidden;

    display: flex;
    align-items: center;
    justify-content: center;

    background-image: url(${bg});
    background-size: cover;
`;

const Container = styled.div`
    display: flex;
    flex-direction: column;
`;

const Content = styled.div`
    max-width: 800px;
    border-radius: 0.5rem;
    padding: 1.15rem;
    background-color: white;
    
    box-shadow: 0 0 4px rgba(0, 0, 0, 1);

    form {
        display: flex;
        flex-direction: column;
    }

    form > * {
        margin: 0.5rem;
    }

    form > :last-child {
        align-self: flex-end;
    }
`;

const Title = styled.h1`
    align-self: flex-start;
    margin-bottom: 0.75rem;
    color: white;
`;

type Props = {
    signup: (email: string, password: string) => Promise<void>
}

const Layout: React.FC<Props> = ({signup}) => {
    return (
        <Root>
            <Container>
                <Title>Sign up</Title>
                <Content>
                    <Form signup={signup}/>
                </Content>
            </Container>
        </Root>
    );
};

export default Layout;
