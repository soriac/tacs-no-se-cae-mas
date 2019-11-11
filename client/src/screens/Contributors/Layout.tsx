import React from 'react';
import styled from 'styled-components';
import {Spinner} from '@blueprintjs/core';
import {ContentContainer} from '../../components/ContentContainer';
import {Root} from '../../components/Root';
import Contributor from '../../components/Contributor';
import {ContributorGithub} from "../../api/types";

const Container = styled(ContentContainer)`
    & > .bp3-input-group {
      align-self: flex-end;    
    }
`;

type Props = {
    contributors: ContributorGithub[]
    loading: boolean
}
const Layout: React.FC<Props> = ({contributors, loading}) => {
    return (
        <Root>
            <Container>
                <h2>Contributors</h2>
                {
                    loading ?
                        <Spinner/>
                        : contributors ?
                        contributors.map(contributor => <Contributor contributor={contributor}/>)
                        : null
                }
            </Container>
        </Root>
    );
};

export default Layout;