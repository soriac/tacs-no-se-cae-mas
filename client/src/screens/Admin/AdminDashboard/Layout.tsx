import React from 'react';
import styled from 'styled-components';
import {Card, Spinner, Switch} from '@blueprintjs/core';
import {DatePicker} from '@blueprintjs/datetime';
import {Root} from '../../../components/Root';
import {ContentContainer} from '../../../components/ContentContainer';
import {ToggleableFilter} from "../RepoSearch";

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
    dateFilterEnabled: boolean
    setDateFilterEnabled: (dateFilterEnabled: boolean) => void
}

const Layout: React.FC<Props> = ({date, setDate, repoCount, loading, dateFilterEnabled, setDateFilterEnabled}) => {
    const toggleEnabled = () => setDateFilterEnabled(!dateFilterEnabled);

    return (
        <Root>
            <ContentContainer>
                <h2>Repository Count</h2>
                <Switch checked={dateFilterEnabled} onChange={toggleEnabled} label={"Filter by date:"}/>
                <Card hidden={!dateFilterEnabled}>
                    <DatePicker
                        value={dateFilterEnabled ? date : undefined}
                        onChange={setDate}
                        modifiers={{}}/>
                </Card>
                {
                    loading ?
                        <Spinner/>
                        : (repoCount === 0 || !!repoCount) ?
                        <TextContainer>
                            <p hidden={!dateFilterEnabled}>Repos in system <strong>since {date.getFullYear()}-{date.getMonth() + 1}-{date.getDate()}</strong>:</p>
                            <p hidden={dateFilterEnabled}>Total repos in system:</p>
                            <Large>{repoCount}</Large>
                        </TextContainer>
                        : null
                }
            </ContentContainer>
        </Root>
    )
};

export default Layout;