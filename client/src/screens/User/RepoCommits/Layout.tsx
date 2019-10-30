import React from 'react';
import {Button, Card} from '@blueprintjs/core';
import {Commit} from '../../../api/types';
import {ContentContainer} from '../../../components/ContentContainer';
import {Root} from '../../../components/Root';
import Form from './Form';
import styled from 'styled-components';

const CommitContainer = styled(Card)`
    width: 100%;
    max-width: 65vw;
`;

type Props = {
    loading: boolean
    commits: Commit[]
    getCommits: (author: string, name: string) => Promise<void>
}
const Layout: React.FC<Props> = ({commits, loading, getCommits}) => {

    return (
        <Root>
            <ContentContainer>
                <Form getCommits={getCommits}/>
                {commits.map(commit => {
                    return (
                        <CommitContainer>
                            <p><strong>{commit.authorName}</strong> - {commit.authorEmail}</p>
                            <p>{commit.message}</p>
                            <small>{commit.date}</small><br/>
                            <small>{commit.sha}</small><br/>
                            <a href={commit.url}>
                                <Button>Ver commit</Button>
                            </a>
                        </CommitContainer>
                    );
                })}
            </ContentContainer>
        </Root>
    );
};

export default Layout;