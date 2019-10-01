import React from 'react';
import styled from 'styled-components';
import {Card, Spinner} from '@blueprintjs/core';
import {DatePicker} from '@blueprintjs/datetime';
import {Root} from '../../../components/Root';
import {ContentContainer} from '../../../components/ContentContainer';

const TextContainer = styled.div`
    margin-bottom: 0;
    & > * {
        margin-bottom: 0.5rem;
    }
`;

const Large = styled.p`
  font-size: 3rem;
  text-align: center;
`;

type Props = {
    date: Date
    setDate: (d: Date) => void
    repoCount: number | undefined
    loading: boolean
}
const Layout: React.FC<Props> = ({date, setDate, repoCount, loading}) => {

    return (
        <Root>
            <ContentContainer>
                <h2>Repository Count</h2>
                <Card>
                    <DatePicker
                        value={date}
                        onChange={setDate}
                        modifiers={{}}/>
                </Card>
                {
                    loading ?
                        <Spinner/>
                        : (repoCount === 0 || !!repoCount) ?
                        <TextContainer>
                            <p>Repos in system since {date.getFullYear()}-{date.getMonth() + 1}-{date.getDate()}:</p>
                            <Large>{repoCount}</Large>
                        </TextContainer>
                        : null
                }
            </ContentContainer>
        </Root>
    )
};

export default Layout;