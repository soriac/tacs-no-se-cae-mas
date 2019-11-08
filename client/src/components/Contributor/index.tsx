import React from 'react';
import styled from 'styled-components';
import {Card} from '@blueprintjs/core';
import {ContributorGithub} from '../../api/types';

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
    contributor: ContributorGithub
}
const Contributor: React.FC<Props> = ({contributor}) => {

    return (
        <Container elevation={2}>
            <h3 style={{wordWrap: 'break-word'}}>
                <a href={contributor.htmlUrl} target="_blank">{contributor.login}</a>
                {
                    contributor.avatarUrl &&
                    <img src={contributor.avatarUrl} style={{width:65, height:65, float:"right"}}/>
                }
            </h3>
            <p><small>{contributor.id}</small></p>
            {isPresent(contributor.contributions) && <p>Contributions: <strong>{contributor.contributions}</strong></p>}

        </Container>
    );

};

export default Contributor;