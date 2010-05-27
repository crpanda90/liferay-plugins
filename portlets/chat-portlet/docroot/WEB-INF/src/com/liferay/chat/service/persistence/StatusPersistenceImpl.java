/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.chat.service.persistence;

import com.liferay.chat.NoSuchStatusException;
import com.liferay.chat.model.Status;
import com.liferay.chat.model.impl.StatusImpl;
import com.liferay.chat.model.impl.StatusModelImpl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="StatusPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       StatusPersistence
 * @see       StatusUtil
 * @generated
 */
public class StatusPersistenceImpl extends BasePersistenceImpl<Status>
	implements StatusPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = StatusImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_USERID = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_MODIFIEDDATE = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByModifiedDate",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_MODIFIEDDATE = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByModifiedDate", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_ONLINE = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByOnline",
			new String[] {
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ONLINE = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByOnline", new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_M_O = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByM_O",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_M_O = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByM_O",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Status status) {
		EntityCacheUtil.putResult(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusImpl.class, status.getPrimaryKey(), status);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
			new Object[] { new Long(status.getUserId()) }, status);
	}

	public void cacheResult(List<Status> statuses) {
		for (Status status : statuses) {
			if (EntityCacheUtil.getResult(
						StatusModelImpl.ENTITY_CACHE_ENABLED, StatusImpl.class,
						status.getPrimaryKey(), this) == null) {
				cacheResult(status);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(StatusImpl.class.getName());
		EntityCacheUtil.clearCache(StatusImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(Status status) {
		EntityCacheUtil.removeResult(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusImpl.class, status.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID,
			new Object[] { new Long(status.getUserId()) });
	}

	public Status create(long statusId) {
		Status status = new StatusImpl();

		status.setNew(true);
		status.setPrimaryKey(statusId);

		return status;
	}

	public Status remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Status remove(long statusId)
		throws NoSuchStatusException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Status status = (Status)session.get(StatusImpl.class,
					new Long(statusId));

			if (status == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + statusId);
				}

				throw new NoSuchStatusException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					statusId);
			}

			return remove(status);
		}
		catch (NoSuchStatusException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Status remove(Status status) throws SystemException {
		for (ModelListener<Status> listener : listeners) {
			listener.onBeforeRemove(status);
		}

		status = removeImpl(status);

		for (ModelListener<Status> listener : listeners) {
			listener.onAfterRemove(status);
		}

		return status;
	}

	protected Status removeImpl(Status status) throws SystemException {
		status = toUnwrappedModel(status);

		Session session = null;

		try {
			session = openSession();

			if (status.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(StatusImpl.class,
						status.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(status);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		StatusModelImpl statusModelImpl = (StatusModelImpl)status;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID,
			new Object[] { new Long(statusModelImpl.getOriginalUserId()) });

		EntityCacheUtil.removeResult(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusImpl.class, status.getPrimaryKey());

		return status;
	}

	public Status updateImpl(com.liferay.chat.model.Status status, boolean merge)
		throws SystemException {
		status = toUnwrappedModel(status);

		boolean isNew = status.isNew();

		StatusModelImpl statusModelImpl = (StatusModelImpl)status;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, status, merge);

			status.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(StatusModelImpl.ENTITY_CACHE_ENABLED,
			StatusImpl.class, status.getPrimaryKey(), status);

		if (!isNew &&
				(status.getUserId() != statusModelImpl.getOriginalUserId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID,
				new Object[] { new Long(statusModelImpl.getOriginalUserId()) });
		}

		if (isNew ||
				(status.getUserId() != statusModelImpl.getOriginalUserId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
				new Object[] { new Long(status.getUserId()) }, status);
		}

		return status;
	}

	protected Status toUnwrappedModel(Status status) {
		if (status instanceof StatusImpl) {
			return status;
		}

		StatusImpl statusImpl = new StatusImpl();

		statusImpl.setNew(status.isNew());
		statusImpl.setPrimaryKey(status.getPrimaryKey());

		statusImpl.setStatusId(status.getStatusId());
		statusImpl.setUserId(status.getUserId());
		statusImpl.setModifiedDate(status.getModifiedDate());
		statusImpl.setOnline(status.isOnline());
		statusImpl.setAwake(status.isAwake());
		statusImpl.setActivePanelId(status.getActivePanelId());
		statusImpl.setMessage(status.getMessage());
		statusImpl.setPlaySound(status.isPlaySound());

		return statusImpl;
	}

	public Status findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Status findByPrimaryKey(long statusId)
		throws NoSuchStatusException, SystemException {
		Status status = fetchByPrimaryKey(statusId);

		if (status == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + statusId);
			}

			throw new NoSuchStatusException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				statusId);
		}

		return status;
	}

	public Status fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Status fetchByPrimaryKey(long statusId) throws SystemException {
		Status status = (Status)EntityCacheUtil.getResult(StatusModelImpl.ENTITY_CACHE_ENABLED,
				StatusImpl.class, statusId, this);

		if (status == null) {
			Session session = null;

			try {
				session = openSession();

				status = (Status)session.get(StatusImpl.class,
						new Long(statusId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (status != null) {
					cacheResult(status);
				}

				closeSession(session);
			}
		}

		return status;
	}

	public Status findByUserId(long userId)
		throws NoSuchStatusException, SystemException {
		Status status = fetchByUserId(userId);

		if (status == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStatusException(msg.toString());
		}

		return status;
	}

	public Status fetchByUserId(long userId) throws SystemException {
		return fetchByUserId(userId, true);
	}

	public Status fetchByUserId(long userId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_USERID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_STATUS_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<Status> list = q.list();

				result = list;

				Status status = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
						finderArgs, list);
				}
				else {
					status = list.get(0);

					cacheResult(status);

					if ((status.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
							finderArgs, status);
					}
				}

				return status;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
						finderArgs, new ArrayList<Status>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Status)result;
			}
		}
	}

	public List<Status> findByModifiedDate(long modifiedDate)
		throws SystemException {
		return findByModifiedDate(modifiedDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<Status> findByModifiedDate(long modifiedDate, int start, int end)
		throws SystemException {
		return findByModifiedDate(modifiedDate, start, end, null);
	}

	public List<Status> findByModifiedDate(long modifiedDate, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(modifiedDate),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Status> list = (List<Status>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_MODIFIEDDATE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_STATUS_WHERE);

				query.append(_FINDER_COLUMN_MODIFIEDDATE_MODIFIEDDATE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modifiedDate);

				list = (List<Status>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Status>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_MODIFIEDDATE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Status findByModifiedDate_First(long modifiedDate,
		OrderByComparator orderByComparator)
		throws NoSuchStatusException, SystemException {
		List<Status> list = findByModifiedDate(modifiedDate, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("modifiedDate=");
			msg.append(modifiedDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatusException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Status findByModifiedDate_Last(long modifiedDate,
		OrderByComparator orderByComparator)
		throws NoSuchStatusException, SystemException {
		int count = countByModifiedDate(modifiedDate);

		List<Status> list = findByModifiedDate(modifiedDate, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("modifiedDate=");
			msg.append(modifiedDate);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatusException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Status[] findByModifiedDate_PrevAndNext(long statusId,
		long modifiedDate, OrderByComparator orderByComparator)
		throws NoSuchStatusException, SystemException {
		Status status = findByPrimaryKey(statusId);

		Session session = null;

		try {
			session = openSession();

			Status[] array = new StatusImpl[3];

			array[0] = getByModifiedDate_PrevAndNext(session, status,
					modifiedDate, orderByComparator, true);

			array[1] = status;

			array[2] = getByModifiedDate_PrevAndNext(session, status,
					modifiedDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Status getByModifiedDate_PrevAndNext(Session session,
		Status status, long modifiedDate, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_STATUS_WHERE);

		query.append(_FINDER_COLUMN_MODIFIEDDATE_MODIFIEDDATE_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(modifiedDate);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(status);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Status> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<Status> findByOnline(boolean online) throws SystemException {
		return findByOnline(online, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Status> findByOnline(boolean online, int start, int end)
		throws SystemException {
		return findByOnline(online, start, end, null);
	}

	public List<Status> findByOnline(boolean online, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				Boolean.valueOf(online),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Status> list = (List<Status>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ONLINE,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_STATUS_WHERE);

				query.append(_FINDER_COLUMN_ONLINE_ONLINE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(online);

				list = (List<Status>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Status>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ONLINE,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Status findByOnline_First(boolean online,
		OrderByComparator orderByComparator)
		throws NoSuchStatusException, SystemException {
		List<Status> list = findByOnline(online, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("online=");
			msg.append(online);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatusException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Status findByOnline_Last(boolean online,
		OrderByComparator orderByComparator)
		throws NoSuchStatusException, SystemException {
		int count = countByOnline(online);

		List<Status> list = findByOnline(online, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("online=");
			msg.append(online);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatusException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Status[] findByOnline_PrevAndNext(long statusId, boolean online,
		OrderByComparator orderByComparator)
		throws NoSuchStatusException, SystemException {
		Status status = findByPrimaryKey(statusId);

		Session session = null;

		try {
			session = openSession();

			Status[] array = new StatusImpl[3];

			array[0] = getByOnline_PrevAndNext(session, status, online,
					orderByComparator, true);

			array[1] = status;

			array[2] = getByOnline_PrevAndNext(session, status, online,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Status getByOnline_PrevAndNext(Session session, Status status,
		boolean online, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_STATUS_WHERE);

		query.append(_FINDER_COLUMN_ONLINE_ONLINE_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(online);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(status);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Status> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<Status> findByM_O(long modifiedDate, boolean online)
		throws SystemException {
		return findByM_O(modifiedDate, online, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<Status> findByM_O(long modifiedDate, boolean online, int start,
		int end) throws SystemException {
		return findByM_O(modifiedDate, online, start, end, null);
	}

	public List<Status> findByM_O(long modifiedDate, boolean online, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(modifiedDate), Boolean.valueOf(online),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Status> list = (List<Status>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_M_O,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_STATUS_WHERE);

				query.append(_FINDER_COLUMN_M_O_MODIFIEDDATE_2);

				query.append(_FINDER_COLUMN_M_O_ONLINE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modifiedDate);

				qPos.add(online);

				list = (List<Status>)QueryUtil.list(q, getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Status>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_M_O, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public Status findByM_O_First(long modifiedDate, boolean online,
		OrderByComparator orderByComparator)
		throws NoSuchStatusException, SystemException {
		List<Status> list = findByM_O(modifiedDate, online, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("modifiedDate=");
			msg.append(modifiedDate);

			msg.append(", online=");
			msg.append(online);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatusException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Status findByM_O_Last(long modifiedDate, boolean online,
		OrderByComparator orderByComparator)
		throws NoSuchStatusException, SystemException {
		int count = countByM_O(modifiedDate, online);

		List<Status> list = findByM_O(modifiedDate, online, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("modifiedDate=");
			msg.append(modifiedDate);

			msg.append(", online=");
			msg.append(online);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatusException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Status[] findByM_O_PrevAndNext(long statusId, long modifiedDate,
		boolean online, OrderByComparator orderByComparator)
		throws NoSuchStatusException, SystemException {
		Status status = findByPrimaryKey(statusId);

		Session session = null;

		try {
			session = openSession();

			Status[] array = new StatusImpl[3];

			array[0] = getByM_O_PrevAndNext(session, status, modifiedDate,
					online, orderByComparator, true);

			array[1] = status;

			array[2] = getByM_O_PrevAndNext(session, status, modifiedDate,
					online, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Status getByM_O_PrevAndNext(Session session, Status status,
		long modifiedDate, boolean online, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_STATUS_WHERE);

		query.append(_FINDER_COLUMN_M_O_MODIFIEDDATE_2);

		query.append(_FINDER_COLUMN_M_O_ONLINE_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(modifiedDate);

		qPos.add(online);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(status);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Status> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<Status> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Status> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Status> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Status> list = (List<Status>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_STATUS);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_STATUS;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Status>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Status>)QueryUtil.list(q, getDialect(), start,
							end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Status>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserId(long userId)
		throws NoSuchStatusException, SystemException {
		Status status = findByUserId(userId);

		remove(status);
	}

	public void removeByModifiedDate(long modifiedDate)
		throws SystemException {
		for (Status status : findByModifiedDate(modifiedDate)) {
			remove(status);
		}
	}

	public void removeByOnline(boolean online) throws SystemException {
		for (Status status : findByOnline(online)) {
			remove(status);
		}
	}

	public void removeByM_O(long modifiedDate, boolean online)
		throws SystemException {
		for (Status status : findByM_O(modifiedDate, online)) {
			remove(status);
		}
	}

	public void removeAll() throws SystemException {
		for (Status status : findAll()) {
			remove(status);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_STATUS_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByModifiedDate(long modifiedDate) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(modifiedDate) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_MODIFIEDDATE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_STATUS_WHERE);

				query.append(_FINDER_COLUMN_MODIFIEDDATE_MODIFIEDDATE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modifiedDate);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_MODIFIEDDATE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByOnline(boolean online) throws SystemException {
		Object[] finderArgs = new Object[] { Boolean.valueOf(online) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ONLINE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_STATUS_WHERE);

				query.append(_FINDER_COLUMN_ONLINE_ONLINE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(online);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ONLINE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByM_O(long modifiedDate, boolean online)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(modifiedDate), Boolean.valueOf(online)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_M_O,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_STATUS_WHERE);

				query.append(_FINDER_COLUMN_M_O_MODIFIEDDATE_2);

				query.append(_FINDER_COLUMN_M_O_ONLINE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modifiedDate);

				qPos.add(online);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_M_O, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_STATUS);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.chat.model.Status")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Status>> listenersList = new ArrayList<ModelListener<Status>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Status>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = EntryPersistence.class)
	protected EntryPersistence entryPersistence;
	@BeanReference(type = StatusPersistence.class)
	protected StatusPersistence statusPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_STATUS = "SELECT status FROM Status status";
	private static final String _SQL_SELECT_STATUS_WHERE = "SELECT status FROM Status status WHERE ";
	private static final String _SQL_COUNT_STATUS = "SELECT COUNT(status) FROM Status status";
	private static final String _SQL_COUNT_STATUS_WHERE = "SELECT COUNT(status) FROM Status status WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "status.userId = ?";
	private static final String _FINDER_COLUMN_MODIFIEDDATE_MODIFIEDDATE_2 = "status.modifiedDate = ?";
	private static final String _FINDER_COLUMN_ONLINE_ONLINE_2 = "status.online = ?";
	private static final String _FINDER_COLUMN_M_O_MODIFIEDDATE_2 = "status.modifiedDate = ? AND ";
	private static final String _FINDER_COLUMN_M_O_ONLINE_2 = "status.online = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "status.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Status exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Status exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(StatusPersistenceImpl.class);
}