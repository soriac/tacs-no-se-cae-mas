import React from 'react';
import {HTMLSelect, InputGroup, Switch} from '@blueprintjs/core';
import {ToggleableFilter} from '../../screens/Admin/RepoSearch';
import {Operator} from '../../api/types';

type Props = {
    title: string
    filter: ToggleableFilter
    setFilter: (filter: ToggleableFilter) => void
}
const RepositorySearchFilter: React.FC<Props> = ({title, filter, setFilter}) => {
    const changeValue = (value: number) => setFilter({...filter, value});
    const changeOp = (op: Operator) => setFilter({...filter, op});
    const toggleEnabled = () => setFilter({...filter, enabled: !filter.enabled});

    return (
        <>
            <Switch checked={filter.enabled} onChange={toggleEnabled}/>
            <p>{title}:</p>
            <HTMLSelect disabled={!filter.enabled} onChange={(e: any) => changeOp(e.target.value)}>
                <option value="lt">Less than</option>
                <option value="gt">Greater than</option>
                <option value="leq">Less than or equal to</option>
                <option value="geq">Greater than or equal to</option>
            </HTMLSelect>
            <InputGroup type='number' disabled={!filter.enabled} value={filter.value.toString()}
                        onChange={(e: any) => changeValue(e.target.value)}/>
        </>
    );
};

export default RepositorySearchFilter;